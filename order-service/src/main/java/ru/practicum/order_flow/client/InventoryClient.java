package ru.practicum.order_flow.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class InventoryClient {
    private final RestClient restClient;

    public InventoryClient(@Value("${inventory.base-url}") String baseUrl) {
        this.restClient = RestClient.create(baseUrl);
    }

    public String ping() {
        return restClient
                .get()
                .uri("/ping")
                .retrieve()
                .body(String.class);
    }


    /*
    public String ping() {

        // Используем настроенный HTTP-клиент.
        // return вернёт результат всей цепочки — значение, полученное от body().
        return restClient

                // Выбираем HTTP-метод GET. Сам запрос здесь ещё не отправляется.
                .get()

                // Задаём путь запроса относительно базового адреса клиента.
                // При базе http://localhost:8082 получится http://localhost:8082/ping.
                .uri("/ping")

                // Переходим к настройке получения и обработки ответа.
                // По умолчанию ответы со статусами 4xx и 5xx приведут к исключению.
                // Сам запрос здесь ещё не отправляется.
                .retrieve()

                // Выполняем HTTP-запрос и ожидаем ответ.
                // String.class указывает, что тело ответа нужно прочитать как строку.
                // При успешном чтении эта строка станет результатом всей цепочки.
                .body(String.class);
    }*/
}
