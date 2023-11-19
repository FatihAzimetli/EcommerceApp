package com.tpe.exeption;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
//public class ConflictException extends Throwable-- Throwable siliyoruz yerine RuntimeException yaziyoruz ve
// altinda public ConflictException(@NotBlank(message = "Email is required!") @Email String s)'
// deki @NotBlank(message = "Email is required!") @Email kismi siliyoruz
//RuntimeException magec diye bir field e var bu massage fieldene nasil set ediyor? Parametreli
// bir constructer ile yani ordaki s yada silip message yazdigimiz field ile set edecek bu durumda runteim
// excepsinin contrucgerine kullanmasini istiyoruz perint cuntructere kullanmasi icin super kiwordtu
// yaziyoruz bu hali ile kendimiz custem bir excetion tanimlamis oluyoruz

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
