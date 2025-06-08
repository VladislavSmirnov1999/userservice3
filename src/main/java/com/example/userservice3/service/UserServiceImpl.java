package com.example.userservice3.service;

import com.example.userservice3.model.User;
import com.example.userservice3.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public User save(User user) {
        logger.info("Создание пользователя: {}", user.getName());
        try {
            userRepository.save(user);
            logger.info("Пользователь успешно создан: {}", user.getName());
            return user;
        } catch (Exception e) {
            logger.error("Не удалось создать пользователя: {}", user.getName());
            throw new RuntimeException(e);
        }
    }

    @Override
    public User update(Long id, User userForUpdate) {
        logger.info("Попытка изменить пользователя с ID: {}", id);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User userFounded = user.get();
            userFounded.setName(userForUpdate.getName());
            userFounded.setAge(userForUpdate.getAge());
            userFounded.setEmail(userForUpdate.getEmail());
            logger.info("Пользователь c ID: {} успешно изменен", id);
            return userFounded;
        } else {
            logger.info("Пользователь c ID: {} не найден для обновления", id);
            throw new RuntimeException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        logger.info("Поиск пользователя по ID: {}", id);
        Optional<User> findUser = userRepository.findById(id);
        if (findUser.isPresent()) {
            logger.info("Пользователь с ID {} найден.", id);
        } else {
            logger.warn("Пользователь с ID {} не найден.", id);
        }
        return findUser;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        logger.info("Поиск всех пользователей");
        List<User> users = userRepository.findAll();
        logger.info("Пользователи найдены в количестве: {}", users.size());
        return users;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
        logger.info("Пользователь с ID {} успешно удален.", id);
    }
}
