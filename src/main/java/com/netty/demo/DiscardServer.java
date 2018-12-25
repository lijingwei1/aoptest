package com.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DiscardServer {
    private int port;
    public DiscardServer(int port){
        this.port = port;
    }
    public void run()throws Exception{
        //Group:群组，Loop:循环，Event：事件
        //Netty内部都是通过线程在处理各种数据，EventLoopGroup就是来管理调度他们的，注册Channel管理他们的生命周期
        //NioEventLoopGroup是一个处理I/O操作的多线程事件循环
        //bossGroup作为boss，接收传入连接
        //因为bossGroup仅接收客户端连接，不做复杂的逻辑处理，为了尽可能减少资源的占用，取值越小越好
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //workLoopGroup作为worker，处理boss接收的连接的流量和将接收的连接注册进入这个worker
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            //ServerBootstrap负责建立服务端，也可以直接使用Channel去建立服务器
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    //指定使用NioServerSocketChannel产生一个channel用来接收连接
                    .channel(NioServerSocketChannel.class)
                    //channelInitializer用于配置一个新的Channel用于向你的Channel当中添加ChannelInboundHandler的实现
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //ChannelPipeline用于存放管理ChannelHandel
                            //ChannelHandler用于处理请求响应的业务逻辑相关代码
                            socketChannel.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    //Option是为了NioServerSocketChannel设置的，用来接收传入连接的
                    //Backlog用于构造服务端套接字ServerSocket对象，标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度。如果未设置或设置的值小于1，Java将使用默认值50
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //childOption是用来给父级ServerChannel之下的channels设置参数的
                    //是否启用心跳保活机制。在双方TCP套接字建立连接后（即都进入ESTABLISHED）并且在两个小时左右上层没有任何数据传输的情况下，这套机制才会被激活
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            //绑定并开始接收传入连接
            ChannelFuture f = b.bind(port).sync();
            //等到服务器套接字关闭
            f.channel().closeFuture().sync();
        }finally {
            //资源释放
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int prot = 8808;
        try{
            new DiscardServer(prot).run();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}























































