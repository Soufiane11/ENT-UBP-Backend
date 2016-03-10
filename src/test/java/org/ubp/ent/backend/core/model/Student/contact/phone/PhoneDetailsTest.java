package org.ubp.ent.backend.core.model.teacher.contact.phone;

import org.junit.Test;
import org.ubp.ent.backend.core.exceptions.model.BadFormattedPhoneNumber;

import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Anthony on 12/01/2016.
 */
public class PhoneDetailsTest {
    private static final String VALID_REGULAR_NUMBER = "06 11 11 11 11";
    private static final String VALID_NATIONAL_NUMBER = "+336 11 11 11 11";
    private static final String VALID_FULL_NATIONAL_NUMBER = "00336 11 11 11 11";

    public static PhoneDetails createOne() {
        StringBuilder sb = new StringBuilder("06");
        sb.append(ThreadLocalRandom.current().nextInt(10, 100));
        sb.append(ThreadLocalRandom.current().nextInt(10, 100));
        sb.append(ThreadLocalRandom.current().nextInt(10, 100));
        sb.append(ThreadLocalRandom.current().nextInt(10, 100));
        return new PhoneDetails(sb.toString());
    }

    public static PhoneDetails createOne(String number) {
        return new PhoneDetails(number);
    }

    public static PhoneDetails[] createSeveral(int howMany) {
        PhoneDetails[] phoneDetails = new PhoneDetails[howMany];
        for (int i = 0; i < howMany; ++i) {
            phoneDetails[i] = createOne();
        }
        return phoneDetails;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithNullNumber() {
        new PhoneDetails(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithEmptyNumber() {
        new PhoneDetails(" ");
    }

    @Test
    public void shouldInstantiate() {
        PhoneDetails model = new PhoneDetails(VALID_REGULAR_NUMBER);
        assertThat(model.getNumber()).isEqualTo(VALID_NATIONAL_NUMBER);
    }

    @Test(expected = BadFormattedPhoneNumber.class)
    public void shouldRejectNonPhoneNumber() {
        new PhoneDetails("abcdefghij");
    }

    /*
    * Regular number : 06 11 11 11 11 (starts with 0X, followed by 8 more digits)
    * National number : +336 11 11 11 11 (starts with +336, followed by 8 more digits)
    * Full National number : 0033 11 11 11 11 (starts with 0X, followed by 8 more digits)
     */
    @Test(expected = BadFormattedPhoneNumber.class)
    public void shouldRejectTooShortRegularNumber() {
        new PhoneDetails("06 12 13");
    }

    @Test(expected = BadFormattedPhoneNumber.class)
    public void shouldRejectTooLongRegularNumber() {
        new PhoneDetails(VALID_REGULAR_NUMBER + " 11");
    }

    @Test(expected = BadFormattedPhoneNumber.class)
    public void shouldRejectTooShortNationalNumber() {
        new PhoneDetails("+336 12 13");
    }

    @Test(expected = BadFormattedPhoneNumber.class)
    public void shouldRejectTooLongNationalNumber() {
        new PhoneDetails(VALID_NATIONAL_NUMBER + " 11");
    }

    @Test(expected = BadFormattedPhoneNumber.class)
    public void shouldRejectTooShortFullNationalNumber() {
        new PhoneDetails("00336 12 13");
    }

    @Test(expected = BadFormattedPhoneNumber.class)
    public void shouldRejectTooLongFullNationalNumber() {
        new PhoneDetails(VALID_FULL_NATIONAL_NUMBER + "11");
    }

    @Test
    public void shouldFormatProperly() {
        // regular
        PhoneDetails phone = new PhoneDetails("06 11 12 13 14");
        assertThat(phone.getNumber()).isEqualTo("+336 11 12 13 14");
        phone = new PhoneDetails("0611121314");
        assertThat(phone.getNumber()).isEqualTo("+336 11 12 13 14");
        phone = new PhoneDetails("06.11.12.13.14");
        assertThat(phone.getNumber()).isEqualTo("+336 11 12 13 14");

        // national
        phone = new PhoneDetails("+336 11 12 13 14");
        assertThat(phone.getNumber()).isEqualTo("+336 11 12 13 14");
        phone = new PhoneDetails("+33611121314");
        assertThat(phone.getNumber()).isEqualTo("+336 11 12 13 14");
        phone = new PhoneDetails("+336.11.12.13.14");
        assertThat(phone.getNumber()).isEqualTo("+336 11 12 13 14");

        //full national
        phone = new PhoneDetails("00336 11 12 13 14");
        assertThat(phone.getNumber()).isEqualTo("+336 11 12 13 14");
        phone = new PhoneDetails("0033611121314");
        assertThat(phone.getNumber()).isEqualTo("+336 11 12 13 14");
        phone = new PhoneDetails("00336.11.12.13.14");
        assertThat(phone.getNumber()).isEqualTo("+336 11 12 13 14");
    }

    @Test
    public void shouldBeEqualByNumber() {
        String number = "06 11 12 13 14";
        assertThat(createOne(number)).isEqualTo(createOne(number));
    }

    @Test
    public void shouldNotBeEqualWithDifferentNumbers() {
        assertThat(createOne()).isNotEqualTo(createOne());
    }

}
