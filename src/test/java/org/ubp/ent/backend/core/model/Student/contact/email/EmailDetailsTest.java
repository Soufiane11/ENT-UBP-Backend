package org.ubp.ent.backend.core.model.teacher.contact.email;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.ubp.ent.backend.core.exceptions.model.BadFormattedEmailAddress;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * Created by Anthony on 13/01/2016.
 */
public class EmailDetailsTest {

    private static final String VALID_EMAIL = "aaa-rrr@ent-ubp.fr";

    public static EmailDetails createOne() {
        int length = ThreadLocalRandom.current().nextInt(10, 15);
        String prefix = RandomStringUtils.randomAlphabetic(length / 2);
        String suffix = RandomStringUtils.randomAlphabetic(length / 2);

        String email = prefix + "@" + suffix + "." + "fr";
        return createOne(email);
    }

    public static EmailDetails createOne(String email) {
        return new EmailDetails(email);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithNullNumber() {
        new EmailDetails(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithEmptyNumber() {
        new EmailDetails(" ");
    }

    @Test
    public void shouldInstantiate() {
        EmailDetails model = new EmailDetails(VALID_EMAIL);
        assertThat(model.getAddress()).isEqualTo(VALID_EMAIL);
    }

    @Test
    public void shouldRejectNonPhoneNumber() {
        List<String> wrongMails = Arrays.asList(
                "abcdefghij",
                "@ent-ubp.fr",
                "aa@ent-ubp",
                "aa@fr"
        );
        for (String mail : wrongMails) {
            try {
                new EmailDetails(mail);
                fail("'" + mail + "' should not be a valid email address");
            } catch (BadFormattedEmailAddress e) {
                assertThat(e.getMessage()).isNotEmpty();
            }
        }
    }


    @Test
    public void shouldBeEqualByEmail() {
        EmailDetails email1 = createOne(VALID_EMAIL);
        EmailDetails email2 = createOne(VALID_EMAIL);
        assertThat(email1).isEqualTo(email2);
    }

    @Test
    public void shouldNotBeEqualWithDifferentNumbers() {
        assertThat(createOne()).isNotEqualTo(createOne());
    }

}
