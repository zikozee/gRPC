package com.zikozee.game.server;

import com.zikozee.game.Die;
import com.zikozee.game.GameState;
import com.zikozee.game.Player;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author : zikoz
 * @created : 15 Sep, 2021
 */

@AllArgsConstructor
public class DieStreamingRequest implements StreamObserver<Die> {

    private Player client;
    private Player server;
    private StreamObserver<GameState> gameStateStreamObserver;

    @Override
    public void onNext(Die die) {
        this.client = this.getNewPlayerPosition(this.client, die.getValue());

        if(this.client.getPosition() != 100){
            this.server = this.getNewPlayerPosition(this.server, ThreadLocalRandom.current().nextInt(1, 7));
        }

        this.gameStateStreamObserver.onNext(getGameState());
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        this.gameStateStreamObserver.onCompleted();
    }

    private GameState getGameState(){
        return GameState.newBuilder()
                .addPlayer(this.client)
                .addPlayer(this.server).build();
    }

    private Player getNewPlayerPosition(Player player, int dieValue){
        int position = player.getPosition() + dieValue;
        position = SnakesAndLadderMap.getPosition(position);// checking Map

        if(position <= 100){
            player = player.toBuilder()
                    .setPosition(position)
                    .build();
        }
        return player;
    }
}
