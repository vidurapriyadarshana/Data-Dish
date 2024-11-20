package edu.ijse.datadish.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class PaymentDto {
    private String payId;
    private String orderId;
    private String amount;
    private String date;
}
