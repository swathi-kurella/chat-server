# chat-bot
* start nio.demo.socket.programming.chat.bot.NIOChatBot app to init chat bot
* Start NIOChatClient1
* Start NIOChatClient2

## Example
### Client1 screen
Connecting to Server on port 1111...

Type your message...

You: Hi server

Server: Hi client1
### Client2 screen
Connecting to Server on port 1111...

Type your message...

You: Hi server

Server: Hi client2
### Server screen
Connection Accepted: /127.0.0.1:1111

Connection Accepted: /127.0.0.1:1111

Client1: Hi server

You: Hi client1

Client2: Hi server

You: Hi client2

# chat-server
* start nio.demo.socket.programming.chat.server.NIOChatServer app to init chat server
* Start Alex user
* Start Jacob user 
* Start Mary user
* Send messages in "receiver:message" format
* View messages in "sender:message" format
## Example
### Jacob screen
Connected to Chat Server on port 1111...

User Registration completed

Alex:Hi (User input)

Alex: Hi

### Alex screen
Connected to Chat Server on port 1111...

User Registration completed

Jacob: Hi

Jacob:Hi (User input)

### Server Screen
Connection Accepted: /127.0.0.1:1111

Registered: Alex => [Device{deviceType='pc', client=/127.0.0.1:54551}]

Connection Accepted: /127.0.0.1:1111

Registered: Jacob => [Device{deviceType='pc', client=/127.0.0.1:54555}]

Connection Accepted: /127.0.0.1:1111

Registered: Mary => [Device{deviceType='pc', client=/127.0.0.1:54559}]

Received conversation: Conversation{from='Jacob', to='Alex', msg='Hi'}

Forwarded to: Alex => [Device{deviceType='pc', client=/127.0.0.1:54551}]

Received conversation: Conversation{from='Alex', to='Jacob', msg='Hi'}

Forwarded to: Jacob => [Device{deviceType='pc', client=/127.0.0.1:54555}]

