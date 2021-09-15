package com.zikozee.game.server;

import com.zikozee.game.Die;
import com.zikozee.game.GameState;
import com.zikozee.game.GamesServiceGrpc;
import com.zikozee.game.Player;
import io.grpc.stub.StreamObserver;

/**
 * @author : zikoz
 * @created : 15 Sep, 2021
 */

public class GameService  extends GamesServiceGrpc.GamesServiceImplBase {

    @Override
    public StreamObserver<Die> roll(StreamObserver<GameState> responseObserver) {
        Player client = Player.newBuilder().setName("client").setPosition(0).build();
        Player server = Player.newBuilder().setName("server").setPosition(0).build();

        return new DieStreamingRequest(client, server, responseObserver);
    }
}
