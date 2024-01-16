package menu;

import domain.enumurations.ExpertStatus;
import domain.userEntity.Expert;
import util.ApplicationContext;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static menu.BaseMenu.*;
import static menu.CustomerMenu.validationEmails;
import static menu.CustomerMenu.validationNames;
import static validation.Validation.generateRandomPassword;

public class ExpertMenu {
    public static Optional<Expert> expertOptional;

    public static void signInOrSignUpExpert() {
        System.out.println("1.sign In");
        System.out.println("2.sign up");


        try {
            int select = scanner.nextInt();
            scanner.nextLine();
            switch (select) {
                case 1 -> signInExpert();

                case 2 -> signUpExpert();

                default -> System.out.println("---Error404---");
            }
        } catch (
                Exception e) {
            e.printStackTrace();
            System.out.println("!!!WRONG!!!");

        }

    }

    public static void signInExpert() throws SQLException {
        boolean isTrue = true;
        while (isTrue) {

            String username = "htrhrth23";


            String password = "I6qp7zn#";

            expertOptional = ApplicationContext.getExpertService().login(username, password);


            if (expertOptional.isPresent()) {
                ExpertStatus expertStatus = expertOptional.get().getExpertStatus();
                if (expertStatus == ExpertStatus.CONFIRMED) {
                    System.out.println("you can use system : ");
                    confirmedExpert();
                }
                isTrue = false;
            } else {
                System.out.println("username and password UnCorrect !!!!\n");
                System.out.println("Please Again ... ");
                firstMenu();

            }
        }
    }

    public static void signUpExpert() throws IOException {

        String FirstName = "ali";
        String validatedFirstName = validationNames(FirstName);

        String LastName = "joe";
        String validatedLastName = validationNames(LastName);

        String newEmailOFExpert = "";

        String emailOfExpert = "asadf@gmail.com";
        String validatedEmail = validationEmails(emailOfExpert);

        List<String> list = ApplicationContext.getExpertService().showEmail();
        if (list.contains(validatedEmail)) {
            System.out.println("i have this email in data base ");
        } else {
            newEmailOFExpert = validatedEmail;
        }

        String userName = "htrhrth23";

        String password = generateRandomPassword();

        LocalDate timeOfSignIn = LocalDate.now();


        byte[] imageData = new byte[0];
        try {

            File file = new File("D:\\منابع مکتب شریف\\final-project\\part1\\src\\main\\java\\image\\CE9A9946.JPG");

            BufferedImage image = ImageIO.read(file);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            imageData = baos.toByteArray();

        } catch (Exception e) {
            System.out.println(" i cant convert it to byte ! ");
        }

        ExpertStatus expertStatus = ExpertStatus.NEW;

        Integer star = 0;

        Expert expert = new Expert(validatedFirstName, validatedLastName, newEmailOFExpert,
                userName, password, timeOfSignIn, imageData, expertStatus, star);

        ApplicationContext.getExpertService().save(expert);
        System.out.println("added to data base !");

        confirmedExpert();
    }

    public static void confirmedExpert() {
        System.out.println("1. change password :");
        // System.out.println("1. change password :");
        System.out.println("enter your select:");

        try {
            int select = scanner.nextInt();
            scanner.nextLine();
            switch (select) {
                case 1 -> changePasswordOfExpert();

                case 2 -> System.out.println("now i have just changePasswordOfExpert method ");


                default -> System.out.println("---Error404---");
            }
        } catch (Exception e) {
            //  e.printStackTrace();
            System.out.println("!!!WRONG!!!");

        }
    }

    public static void changePasswordOfExpert() throws SQLException {
        ApplicationContext.getExpertService().changePassword(2, "Fdf@er52");
        System.out.println("password is change !");

        firstMenu();
    }

}
