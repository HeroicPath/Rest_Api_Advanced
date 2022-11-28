package com.epam.esm.RestApiAdvanced.controller;

import com.epam.esm.RestApiAdvanced.dto.OrderDto;
import com.epam.esm.RestApiAdvanced.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users") @AllArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * <p>
     * Creates a new User
     * </p>
     */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> createUser() {
        userService.createUser();
        return ResponseEntity.ok("New user has been created!");
    }

    /**
     * <p>
     * Places Order for the specified User
     * </p>
     *  @param id the User that places Order
     * @param giftCertificateId the Id of Gift Certificate that is being purchased
     */
    @PostMapping(value = "/{id}/order", params = "giftCertificateId")
    public OrderDto placeOrder(@PathVariable @Valid Long id,
                               @RequestParam @Valid Long giftCertificateId) {
        return userService.createOrder(id, giftCertificateId);
    }

    /**
     * <p>
     * Deletes a User by id
     * </p>
     *  @param id the id of User that needs to be deleted
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAUserById(@PathVariable @Valid Long id){
        userService.deleteById(id);
        return ResponseEntity.ok("A user by id " + id + " was deleted!");
    }
}
