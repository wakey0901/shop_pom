package com.qf.shop_service_goods;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RobbitMQConfiguration {

    //交换机
    public static final String FANOUT_NAME = "goods_fanoutExchange";

    /**
     * 声明队列1
     */
    @Bean
    public Queue getQueue1(){
        return new Queue("goods_queue1");
    }

    /**
     * 声明队列2
     */
    @Bean
    public Queue getQueue2(){
        return new Queue("goods_queue2");
    }

    /**
     * 声明交换机
     */
    @Bean
    public FanoutExchange getFanoutExchange(){
        return new FanoutExchange(FANOUT_NAME);
    }

    /**
     * 将队列绑定到交换机上
     */
    @Bean
    public Binding getBinding1(){
        return BindingBuilder.bind(getQueue1()).to(getFanoutExchange());
    }

    @Bean
    public Binding getBinding2(){
        return BindingBuilder.bind(getQueue2()).to(getFanoutExchange());
    }
}
