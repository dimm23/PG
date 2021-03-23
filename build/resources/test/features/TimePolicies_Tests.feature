#language: ru
@time_policies
Функционал: Временные политики
  @add_new_time_policy
  Структура сценария: Временные политики успешно добавляются
    Дано В системе отсутствует временная политика "<имя политики>"
    Когда админ добавляет новую временную политику "<имя политики>"
    Тогда админ видит в таблице "<имя политики>"
    Примеры:
      | имя политики    |
      | test_timepolicy |

  @edit_time_policy
  Структура сценария: Временные политики успешно редктируются
    Дано В системе существует временная политика "<имя политики>"
    Когда Админ выбирает политику "<имя политики>" и нажимает кнопку "Редактировать"
    И админ заполняет форму Настройка времени запрета печати с именем "<имя политики>2"
    Тогда админ видит в таблице "<имя политики>2"
    Примеры:
      | имя политики    |
      | test_timepolicy |

  @delete_time_policy
  Структура сценария: Временные политики успешно удаляются
    Дано В системе существует временная политика "<имя политики>"
    Когда Админ выбирает политику "<имя политики>" и нажимает кнопку "Удалить"
    И админ кликает кнопку "Удалить"
    Тогда политика "<имя политики>" удаляется из таблицы
    Примеры:
      | имя политики    |
      | test_timepolicy |

  ### integration tests ###
  @cant_print_with_time_policy
  Сценарий: Документ не печатается если установлен запрет во временных политиках
    Дано В системе установлена запрещающая временная политика
    И на компьютере пользователя настроена Односторонняя чернобелая печать
    Когда админ кликает ссылку "Журнал"
    Когда пользователь печатает "1doc" файл
    Тогда админ на странице Журналы видит статус "Отказ" отправленного на печать файла и в Деталях "Правило по времени"

  @accessibleAndDeniableTimePolicy
  Сценарий: Документ не печатается если установлена запрещающая временная политика и разрешающая
    Дано В системе установлена запрещающая временная политика
    И в системе установлена разрешающая временная политика
    И на компьютере пользователя настроена Односторонняя чернобелая печать
    Когда админ кликает ссылку "Журнал"
    Когда пользователь печатает "1doc" файл
    Тогда админ на странице Журналы видит статус "Отказ" отправленного на печать файла и в Деталях "Правило по времени"