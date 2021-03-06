#language: ru
@watermark_policies
Функционал: Политики водяных знаков

  @add_watermark_policy
  Структура сценария: Политики водяных знаков успешно добавляются
    Дано В системе отсутствует политика водяных знаков "<имя политики>"
    Когда админ добавляет новую политику водяных знаков "<имя политики>"
    Тогда админ видит в таблице "<имя политики>"
    Примеры:
      | имя политики |
      | test_watermark_policy |

  @edit_watermark_policy
  Структура сценария: Политики водянных знаков успешно редактируются
    Дано В системе существует политика водяных знаков "<имя политики>"
    Когда Админ выбирает политику "<имя политики>" и нажимает кнопку "Редактировать"
    И админ заполняет форму Параметры политики водяных знаков с именем "<имя политики>2"
    Тогда админ видит в таблице "<имя политики>2"
    Примеры:
      | имя политики |
      | test_watermark_policy |

  @delete_watermark_policy
  Структура сценария: Политики водяных знаков успешно удаляются
    Дано В системе существует политика водяных знаков "<имя политики>"
    Когда Админ выбирает политику "<имя политики>" и нажимает кнопку "Удалить"
    И админ кликает кнопку "Удалить"
    Тогда политика "<имя политики>" удаляется из таблицы

    Примеры:
      | имя политики |
      | test_watermark_policy |

  #@read_qr_code
  #Сценарий: На странице Расследования успешно расшифровывается QR код
  #  Дано админ на странице логин
  #  Когда админ вводит логин и пароль
  #  И админ кликает ссылку "Политики"
  #  И админ кликает ссылку "Водяные знаки"
  #  И админ кликает ссылку "Расследование"
  #  И админ загружает изображение "qr_test"
  #  И админ выделяет QR код на странице Расследования
  #  Тогда админ видит распознанный текст "qr_test" QR изображения


