# language: ru
@operators
Функционал: Тестирование страницы Настройки Администрирование Операторы
  @Add_new_operator
  Структура сценария: Новый оператор успешно создаётся
    Дано в системе отсутствует оператор "<логин>"
    Когда админ добавляет нового оператора с логином "<логин>" и почтой "<почта>"
    Тогда админ видит в таблице "<логин>"
    Примеры:
      | логин        | почта      |
      | operator_evm | test@pg.ru |

  @delete_operator
  Структура сценария: Оператор успешно удаляется
    Дано в системе существует оператор "<логин>"
    Когда админ выбирает оператора "<логин>" и кликает кнопку "Удалить"
    И админ кликает кнопку "Удалить"
    Тогда админ не видит в таблице "<логин>"
    Примеры:
      | логин        |
      | operator_evm |

  @edit_operator
  Структура сценария: Оператор успешно редактируется
    Дано в системе существует оператор "<логин>"
    Когда админ выбирает оператора "<логин>" и кликает кнопку "Редактировать"
    И админ заполняет форму Параметры оператора с логином "<логин>" и почтой "<почта>ru"
    И админ кликает кнопку "Сохранить"
    Тогда админ видит в таблице "<почта>ru"
    Примеры:
      | логин        | почта      |
      | operator_evm | test@pg.ru |