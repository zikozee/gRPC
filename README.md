Status Codes
============
https://developers.google.com/maps-booking/reference/grpc-api-v2/status_codes


Response: Streaming vs Single
=============================

Streaming
- Size -Potentially Large/ unknown
  - pagination
- Receiving side might take too much time to process
  - File Upload
- more efficient than multiple RPC calls
- Bi-Directional Stream -> Client/Server streams are completely independent

Single
 - More efficient than streaming RPC
 - Size is small


            KEY VALUE PAIRS:
            ===============
Sending info from client to interceptor: CallOptions
Sending info from client to Server: Metadata
Sending info from interceptor(on server) to Server service: Context



Become CA:

create a private key first for CA (des3 is encryption standard)

openssl genrsa -des3 -out ca.key.pem 2048

create ca certificate (This certificate is used to sign the server certificate and client will use this later)

openssl req -x509 -new -nodes -key ca.key.pem -sha256 -days 365 -out ca.cert.pem



Server:

create a private key for your server

openssl genrsa -out localhost.key 2048

As service owner, you create a request for sending to CA

openssl req -new -key localhost.key -out localhost.csr
  ==== set Common Name(server name): localhost



CA signs your request

CA signs your request which you need to keep it safe

openssl x509 -req -in localhost.csr -CA ca.cert.pem -CAkey ca.key.pem -CAcreateserial -out localhost.crt -days 365



gRPC specific step:

grpc expects the localhost key in PKCS8 standard

openssl pkcs8 -topk8 -nocrypt -in localhost.key -out localhost.pem

-----------------------------------------
