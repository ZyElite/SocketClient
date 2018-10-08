## SocketClient
>IM项目封装 包含服务器(Kotlin) 结合realm（响应式） 数据库 优雅实现即时通讯 并及时展示到界面上

## Import
>待更新

## Updates
- 0.0.1

 第一版封装
## Usage

### 客户端配置(复制到项目中 即可使用 实现IM so easy)

        SocketPacketConfig.setDefaultPacket(true)
                //是否发送心跳 默人false
                .setSendHeartBeat(true)
                //设置套接字地址
                .setSocketAddress("192.168.98.110", 10010, 10000)
                //设置自定义接收解析方式  如果不设置则执行默认方式解析 不按照格式发送则不能解析数据
                .setCustomizeReceive(object : SocketCustomizeReceive {
                    override fun headLength(): Int {
                        //返回包协议头的长度
                        return headLength
                    }

                    override fun bodyLength(head: ByteArray): Int {
                        //解析包头返回包体的长度
                        return bodyLength
                    }

                })
		//注册监听消息回调

        SocketClient.registerRes(object : SocketResponse {
            override fun onConnected() {
               //连接成功回调
            }

            override fun onDisconnected(str: String) {
                //断开连接回调
            }

            override fun onResponse(str: String) {
               //接收到消息回调
            }
        })
		//配置socket 执行连接方法  
        SocketClient.connect()
		//socket 连接完毕 调用发送方法 并制定编码发送
        SocketClient.send(content.toByteArray(Charsets.UTF_8))
### 启动服务器端 servic
> 这样刻客户端就可以与服务器端竞进行通讯了---暂时服务器端很简单。主要任务是先封装客户端IM---SDK
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
