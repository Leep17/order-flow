package ru.practicum.order_flow.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import ru.practicum.order_flow.exception.InventoryUnavailableException;

import java.time.Duration;

@Component
public class InventoryClient {
    private final RestClient restClient;

    public InventoryClient(@Value("${inventory.base-url}") String baseUrl, RestClient.Builder builder) {
        ClientHttpRequestFactorySettings settings =
                ClientHttpRequestFactorySettings
                        .defaults()
                        .withTimeouts(
                                Duration.ofSeconds(2),
                                Duration.ofSeconds(3)
                        );
        ClientHttpRequestFactory clientHttpRequestFactory = ClientHttpRequestFactoryBuilder.detect().build(settings);
        this.restClient = builder.requestFactory(clientHttpRequestFactory).baseUrl(baseUrl).build();

    }

    public String ping() {
        try {
            return restClient
                    .get()
                    .uri("/ping")
                    .retrieve()
                    .body(String.class);
        } catch (ResourceAccessException e) {
            throw new InventoryUnavailableException("Сервис остатков временно недоступен", e);
        }
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

    /*// Создаём объект с настройками для ClientHttpRequestFactory.
// Сам settings ничего не выполняет — он только хранит параметры,
// которые потом будут применены при создании factory.
//
// В нашем случае:
// connect timeout = максимум 2 секунды на установление соединения;
// read timeout = максимум 3 секунды ожидания ответа после соединения.
    ClientHttpRequestFactorySettings settings =
            ClientHttpRequestFactorySettings
                    .defaults()
                    .withTimeouts(
                            Duration.ofSeconds(2),
                            Duration.ofSeconds(3)
                    );


    // ClientHttpRequestFactory — это компонент, через который RestClient
// создаёт и выполняет низкоуровневые HTTP-запросы
// с заданными сетевыми настройками.
//
// То есть factory — НЕ сам HTTP-запрос и НЕ RestClient.
// Она является механизмом между RestClient и конкретным HTTP-транспортом.
//
// ClientHttpRequestFactoryBuilder — интерфейс builder'а для создания factory.
//
// detect() — static-метод самого интерфейса.
// Он определяет доступную реализацию HTTP-клиента и возвращает
// конкретный объект builder'а, реализующий ClientHttpRequestFactoryBuilder.
//
// build(settings) вызывается уже у этого конкретного builder-объекта.
// Он берёт наши settings и создаёт ClientHttpRequestFactory,
// в которой будут применены connect timeout и read timeout.
    ClientHttpRequestFactory clientHttpRequestFactory =
            ClientHttpRequestFactoryBuilder
                    .detect()
                    .build(settings);


// RestClient.Builder — builder для настройки и создания RestClient.
//
// requestFactory(...) передаёт RestClient нашу ClientHttpRequestFactory.
// Поэтому HTTP-запросы этого RestClient будут выполняться через эту factory
// и, соответственно, с заданными в ней timeout.
//
// baseUrl(...) задаёт базовый адрес inventory-service,
// например http://localhost:8082.
//
// build() завершает настройку и создаёт готовый RestClient.
//
// RestClient — это HTTP-клиент, через который order-service
// отправляет HTTP-запросы в inventory-service
// и получает/обрабатывает HTTP-ответы.
this.restClient = builder
            .requestFactory(clientHttpRequestFactory)
            .baseUrl(baseUrl)
        .build();*/
}
