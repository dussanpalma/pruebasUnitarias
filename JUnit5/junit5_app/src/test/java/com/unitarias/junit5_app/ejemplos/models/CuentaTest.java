package com.unitarias.junit5_app.ejemplos.models;

import com.unitarias.junit5_app.ejemplos.models.exceptions.DineroInsuficienteExeption;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CuentaTest {

    public CuentaTest() {
    }

    @Test
//    @Disabled
    @DisplayName("Probando el nombre de la cuenta")

    public void testNombreCuenta() {

//        fail();  
        Cuenta cuenta = new Cuenta("Dussan", new BigDecimal("1000.12345"));
//      cuenta.setPersona("Dussan");
        String esperado = "Dussan";
        String real = cuenta.getPersona();
        Assertions.assertNotNull(real, () -> "La cuenta no puede estar vacia");
        Assertions.assertEquals(esperado, real, () -> "El nombre de la cuenta no es el que se esperaba: se esperaba:" + esperado);
        Assertions.assertTrue(real.equalsIgnoreCase(esperado), () -> "El esperado debe ser igual al real");
    }

    @Test
    public void testSaldoCuenta() {
        Cuenta cuenta = new Cuenta("Dussan", new BigDecimal("1000.12345"));
        Assertions.assertNotNull(cuenta.getSaldo());
        Assertions.assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        Assertions.assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        Assertions.assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    @DisplayName("Probando luna referencia en la cuenta")
    public void testRerefernciaCuenta() {

        Cuenta cuenta = new Cuenta("Dussan Prueba", new BigDecimal("8900.9997"));
        Cuenta cuenta2 = new Cuenta("Dussan Prueba", new BigDecimal("8900.9997"));
//      Assertions.assertNotEquals(cuenta, cuenta2);
        Assertions.assertEquals(cuenta, cuenta2);

    }

    @Test
    public void testDebitoCuenta() {

        Cuenta cuenta = new Cuenta("Dussan Prueba", new BigDecimal("1000.12345"));
        cuenta.debito(new BigDecimal(100));
        Assertions.assertNotNull(cuenta.getSaldo());
        Assertions.assertEquals(900, cuenta.getSaldo().intValue());
        Assertions.assertEquals("900.12345", cuenta.getSaldo().toPlainString());

    }

    @Test
    public void testCreditoCuenta() {

        Cuenta cuenta = new Cuenta("Dussan Prueba", new BigDecimal("1000.12345"));
        cuenta.credito(new BigDecimal(100));
        Assertions.assertNotNull(cuenta.getSaldo());
        Assertions.assertEquals(1100, cuenta.getSaldo().intValue());
        Assertions.assertEquals("1100.12345", cuenta.getSaldo().toPlainString());

    }

    @Test
    public void testDineroInsuficienteExceptionCuenta() {

        Cuenta cuenta = new Cuenta("Dussan Prueba", new BigDecimal("1000.12345"));
        Exception exception = Assertions.assertThrows(DineroInsuficienteExeption.class, () -> {
            cuenta.debito(new BigDecimal(15000));
        });
        String actual = exception.getMessage();
        String esperado = "Dinero Insuficiente";
        Assertions.assertEquals(esperado, actual);
    }

    @Test
    public void testTransferirDineroCuentas() {
        Cuenta cuenta = new Cuenta("Dussan Prueba", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Duvan Prueba", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.setNombre("Bancolombia");
        banco.transferir(cuenta2, cuenta, new BigDecimal(500));

        Assertions.assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        Assertions.assertEquals("3000", cuenta.getSaldo().toPlainString());

    }

    @Test
    public void testRelacionBancoCuentas() {
        Cuenta cuenta = new Cuenta("Dussan Prueba", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Duvan Prueba", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta);
        banco.addCuenta(cuenta2);

        banco.setNombre("Bancolombia");
        banco.transferir(cuenta2, cuenta, new BigDecimal(500));

        Assertions.assertAll(
                () -> {
                    Assertions.assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
                },
                () -> {
                    Assertions.assertEquals("3000", cuenta.getSaldo().toPlainString());
                },
                () -> {
                    Assertions.assertEquals(2, banco.getCuentas().size());
                },
                () -> {
                    Assertions.assertEquals("Bancolombia", cuenta.getBanco().getNombre());
                },
                () -> {
                    Assertions.assertEquals("Dussan Prueba", banco.getCuentas().stream()
                            .filter(c -> c.getPersona().equals("Dussan Prueba")).findFirst()
                            .get().getPersona());
                },
                () -> {
                    Assertions.assertTrue(banco.getCuentas().stream()
                            .anyMatch(c -> c.getPersona().equals("Dussan Prueba")));
                });

    }

}
