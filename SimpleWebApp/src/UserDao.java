import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.UserEntity;

public class UserDao {
    

    // Метод для получения соединения с базой данных
    private Connection getConnection() throws NamingException, SQLException {
        // Получаем контекст JNDI
        InitialContext initialContext = new InitialContext();
        // Получаем ссылку на ресурс данных JNDI
        DataSource dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/webapproles");

        // Выводим информацию о подключенной базе данных
        System.out.println("Connected to database: " + dataSource.getConnection().getCatalog());

        // Получаем соединение с базой данных из ресурса данных
        return dataSource.getConnection();
    }

    // Метод для получения пользователя по ID
    public UserEntity getUserById(int id_user) {
        try (Connection connection = getConnection()) {
            // Подготавливаем запрос к базе данных
            String sql = "SELECT * FROM users WHERE id_user = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id_user);
                // Выполняем запрос
                System.out.println("!!!Работает!!!");
                try (ResultSet resultSet = statement.executeQuery()) {
                    System.out.println("Не пустой");
                    // Если результат не пустой, создаем объект UserEntity и заполняем его данными
                    if (resultSet.next()) {
                        UserEntity user = new UserEntity();
                        user.setIdUser(resultSet.getInt("id_user"));
                        user.setName(resultSet.getString("name"));
                        user.setPassword(resultSet.getString("password"));
                        user.setEmail(resultSet.getString("email"));
                        user.setRole(resultSet.getString("role"));
                        return user;
                    }
                }
            }
        } catch (NamingException | SQLException e) {
            e.printStackTrace(); // Обработка ошибок доступа к базе данных
        }

        return null; // Если пользователь не найден
    }
    // Метод для проверки логина и пароля пользователя
    public boolean checkLogin(String name, String password) {
        try (Connection connection = getConnection()) {
            // Подготавливаем запрос к базе данных
            String sql = "SELECT * FROM users";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                // Выполняем запрос
                try (ResultSet resultSet = statement.executeQuery()) {
                    // Перебираем все комбинации имен и паролей
                    while (resultSet.next()) {
                        String storedName = resultSet.getString("name");
                        String storedPassword = resultSet.getString("password");
                        // Если найдено совпадение, возвращаем true
                        if (name.equals(storedName) && password.equals(storedPassword)) {
                            return true;
                        }
                    }
                }
            }
        } catch (NamingException | SQLException e) {
            e.printStackTrace(); // Обработка ошибок доступа к базе данных
        }
        // Если не найдено совпадение, возвращаем false
        return false;
    }

    public void saveUser(UserEntity user) {
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO users (id_user, name, password, email) VALUES (null, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, user.getName());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getEmail());
                statement.executeUpdate();
            }
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
    }

    public String getUserRole(String name, String pass) {
        // Создать экземпляр UserDao для доступа к данным пользователей
        UserDao userDao = new UserDao();

        // Получить пользователя по имени и паролю
        UserEntity user = userDao.getUserByNameAndPassword(name, pass);

        // Проверить, существует ли пользователь
        if (user != null) {
            // Вернуть роль пользователя
            return user.getRole();
        } else {
            // Если пользователь не найден, вернуть null или другое значение по умолчанию
            return null;
        }
    }

    public UserEntity getUserByNameAndPassword(String name, String password) {
        UserEntity user = null;

        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM users WHERE name = ? AND password = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, password);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // Создать объект UserEntity и заполнить его данными из результата запроса
                        user = new UserEntity();
                        user.setIdUser(resultSet.getInt("id_user"));
                        user.setName(resultSet.getString("name"));
                        user.setPassword(resultSet.getString("password"));
                        user.setEmail(resultSet.getString("email"));
                        user.setRole(resultSet.getString("role"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    public boolean checkAdminLogin(String name, String pass) {
        try (Connection connection = getConnection()) {
            String sql = "SELECT COUNT(*) FROM admins WHERE name = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, pass);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0; // Возвращаем true, если найдена хотя бы одна запись
                    }
                }
            }
        } catch (NamingException | SQLException e) {
            e.printStackTrace(); // Обработка ошибок доступа к базе данных
        }
        return false; // Если не найдено ни одной записи
    }



}
