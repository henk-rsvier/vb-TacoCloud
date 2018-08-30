package tacos.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;
import tacos.domain.Order;
import tacos.repositories.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
	
	private final OrderRepository orderRepository;
	
	
	@Autowired
	public OrderController(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@GetMapping("/current")
	public String orderForm(Model model) {
		return "orderform";
	}
		
	@PostMapping
	public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus, Model model) {
		if(errors.hasErrors()) {
			log.info("Order submitted with errors: {}", order);
			return "orderform";
		}
		log.info("Order submitted: {}", order);
		Order savedOrder = orderRepository.save(order);
		log.info("Order after submitting: {}", savedOrder);
		sessionStatus.setComplete();
		model.addAttribute("finalorder", savedOrder);
		return "/order";
	}
}
