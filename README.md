## SocketClient
>IM项目封装 包含服务器(Kotlin) 结合realm（响应式） 数据库 优雅实现即时通讯 并及时展示到界面上

## Import
>待补

## Updates
- 1.0.0 

 第一版封装
## Usage

### 客户端配置
	SocketPacketConfig.setDefaultPacket(true)//发送是否加入默认包头 分包处理
                .setSendHeartBeat(true)//是否发送心跳包
                .setSocketAddress("192.168.98.110", 10010, 10000)//设置套接字地址
	 //注册 socket 消息监听 运行在子线程
        SocketClient.registerRes(object : SocketResponse {
            override fun onConnected() {
               //连接成功
            }

            override fun onDisconnected(str: String) {
               //断开连接 
            }

            override fun onResponse(str: String) {
               //收到消息
            }
        })
        //连接socket
	SocketClient.connect()
### 启动服务器端 service
> 就可以聊天了
## License
	Copyright (c) 2016-present, RxJava Contributors.
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
