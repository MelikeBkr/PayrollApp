package com.mell.payroll.assembler;

import com.mell.payroll.Status;
import com.mell.payroll.controller.OrderController;
import com.mell.payroll.model.Order;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>>
{
    @Override
    public EntityModel<Order> toModel(Order order)
    {
        EntityModel<Order> orderModel = EntityModel.of(order,
                linkTo(methodOn(OrderController.class).getOrderById(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).getAllOrders()).withRel("orders"));
        if(order.getStatus() == Status.IN_PROGRESS)
        {
            //NOTE: These links are ONLY shown when the order’s status is Status.IN_PROGRESS
            orderModel.add(linkTo(methodOn(OrderController.class).cancelOrder(order.getId()))
                    .withRel("cancel"));
            orderModel.add(linkTo(methodOn(OrderController.class).completeOrder(order.getId()))
                    .withRel("complete"));
        }
        return orderModel;
    }
}
