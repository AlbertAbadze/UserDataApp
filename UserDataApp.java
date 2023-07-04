import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UserDataApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите данные пользователя в формате: Фамилия Имя Отчество датарождения номертелефона пол");
        String userInput = scanner.nextLine();

        String[] userData = userInput.split(" ");

        if (userData.length != 6) {
            System.err.println("Ошибка: неверное количество данных.");
            return;
        }

        String lastName = userData[0];
        String firstName = userData[1];
        String middleName = userData[2];
        String dateOfBirth = userData[3];
        String phoneNumber = userData[4];
        String gender = userData[5];

        try {
            validateData(lastName, firstName, middleName, dateOfBirth, phoneNumber, gender);
            saveUserDataToFile(lastName, firstName, middleName, dateOfBirth, phoneNumber, gender);
            System.out.println("Данные успешно сохранены.");
        } catch (InvalidDataException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл:");
            e.printStackTrace();
        }
    }

    private static void validateData(String lastName, String firstName, String middleName, String dateOfBirth,
                                     String phoneNumber, String gender) throws InvalidDataException {
        validateName(lastName, "Фамилия");
        validateName(firstName, "Имя");
        validateName(middleName, "Отчество");
        validateDateOfBirth(dateOfBirth);
        validatePhoneNumber(phoneNumber);
        validateGender(gender);
    }

    private static void validateName(String name, String fieldName) throws InvalidDataException {
        if (name.isEmpty()) {
            throw new InvalidDataException(fieldName + " не может быть пустым.");
        }
    }

    private static void validateDateOfBirth(String dateOfBirth) throws InvalidDataException {
        if (!dateOfBirth.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
            throw new InvalidDataException("Неверный формат даты рождения. Ожидается dd.mm.yyyy");
        }
    }

    private static void validatePhoneNumber(String phoneNumber) throws InvalidDataException {
        try {
            Long.parseLong(phoneNumber);
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Неверный формат номера телефона. Ожидается целое число.");
        }
    }

    private static void validateGender(String gender) throws InvalidDataException {
        if (!gender.matches("[fm]")) {
            throw new InvalidDataException("Неверный формат пола. Ожидается 'f' или 'm'.");
        }
    }

    private static void saveUserDataToFile(String lastName, String firstName, String middleName, String dateOfBirth,
                                           String phoneNumber, String gender) throws IOException {
        String fileName = lastName + ".txt";
        String userData = lastName + firstName + middleName + dateOfBirth + " " + phoneNumber + gender;

        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.write(userData + System.lineSeparator());
        }
    }

    private static class InvalidDataException extends Exception {
        public InvalidDataException(String message) {
            super(message);
        }
    }
}
