package vn.minhtung.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.minhtung.laptopshop.domain.Order;
import vn.minhtung.laptopshop.service.OrderService;

@Controller
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/admin/order")
    public String getOrder(Model model, @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                // convert from String to int
                page = Integer.parseInt(pageOptional.get());
            } else {
                // page = 1
            }
        } catch (Exception e) {
            // page = 1
            // TODO: handle exception
        }

        Pageable pageable = PageRequest.of(page - 1, 1);
        Page<Order> ordersPage = this.orderService.getAllOrders(pageable);
        List<Order> orders = ordersPage.getContent();
        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", ordersPage.getTotalPages());
        return "admin/order/show";
    }

    @GetMapping("/admin/order/{id}")
    public String getDetailPage(Model model, @PathVariable long id) {
        Order orders = this.orderService.getOrderById(id).get();
        model.addAttribute("orders", orders);
        model.addAttribute("id", id);
        model.addAttribute("orderDetails", orders.getOrderDetails());
        return "admin/order/detail";
    }

    @GetMapping("/admin/order/update/{id}")
    public String getUpdateOrderPage(Model model, @PathVariable long id) {
        Optional<Order> currentOrder = this.orderService.getOrderById(id);
        model.addAttribute("newOrder", currentOrder.get());
        return "admin/order/update";
    }

    @PostMapping("/admin/order/update")
    public String getUpdateOrder(@ModelAttribute("newOrder") Order order) {
        this.orderService.updateOrder(order);
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/delete/{id}")
    public String getDeletePage(Model model, @PathVariable long id) {
        Order order = this.orderService.getOrderById(id).get();
        model.addAttribute("newOrder", order);
        return "admin/order/delete";
    }

    @PostMapping("/admin/order/delete")
    public String getDeleteOrder(@ModelAttribute("newOrder") Order order) {
        this.orderService.deleteOrder(order.getId());
        return "redirect:/admin/order";
    }
}
