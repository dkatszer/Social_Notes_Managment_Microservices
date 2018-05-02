import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by doka on 2017-12-28.
 */
public class PasswordEncoderTest {

    PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    public void encodePassword(){
        System.out.println(encoder.encode("admin"));
        System.out.println(encoder.encode("user"));
        System.out.println(encoder.encode("student"));
        System.out.println(encoder.encode("guest"));
    }
}
