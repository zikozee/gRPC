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

