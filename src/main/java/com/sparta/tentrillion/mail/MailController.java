package com.sparta.tentrillion.mail;

import com.sparta.tentrillion.mail.dto.MailRequestDto;
import com.sparta.tentrillion.mail.dto.VerifyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/email")
public class MailController {

    private final MailService mailService;

    @PostMapping
    public ResponseEntity<Void> sendMail(@RequestBody MailRequestDto mailRequestDto){

        return ResponseEntity.status(HttpStatus.OK).body(mailService.sendMail(mailRequestDto));

    }

    @PutMapping("/verification")
    public ResponseEntity<Void> verifyMail(@RequestBody VerifyRequestDto verifyRequestDto){
        mailService.verifyMail(verifyRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(null);

    }

}
