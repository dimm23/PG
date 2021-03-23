# language: ru

Функционал: Создание редактирование и синхронизация пользователей
  @new_user
  Структура сценария: Новый пользователь успешно добавляется
    Дано В системе отсутствует пользователь "<имя пользователя>"
    Когда админ добавляет нового пользователя с логином "<имя пользователя>"
    Тогда админ видит в таблице "<имя пользователя>"
    Примеры:
      | имя пользователя |
      | test_userName    |

  @edit_user
  Структура сценария: Пользователь успешно редактируется
    Дано В системе существует пользователь "<имя пользователя>"
    Когда Админ выбирает пользователя "<имя пользователя>" и нажимает кнопку "Редактировать"
    И админ заполняет форму Параметры пользователя с логином "<имя пользователя>2"
    Тогда админ видит в таблице "<имя пользователя>2"
    Примеры:
      | имя пользователя |
      | test_userName    |

  @new_group_of_users
  Структура сценария: Группа пользователей успешно создаётся
    Дано В системе отсутствует группа пользователей "<название группы>"
    Когда админ добавляет новую группу пользователей "<название группы>"
    Тогда админ видит в таблице "<название группы>"
    Примеры:
      | название группы |
      | ласковый_май    |


  @edit_group_of_users
  Структура сценария: Группа пользователей успешно редактируется
    Дано В системе существует группа пользователей "<название группы>"
    Когда Админ выбирает группу пользователей "<название группы>" и нажимает кнопку "Редактировать"
    И админ заполняет форму Параметры группы с наименованием "<название группы>2"
    Тогда админ видит в таблице "<название группы>2"
    Примеры:
      | название группы |
      | ласковый_май    |

  @delete_group_of_users
  Структура сценария: Группа пользователей успешно удаляется
    Дано В системе существует группа пользователей "<название группы>"
    Когда Админ выбирает группу пользователей "<название группы>" и нажимает кнопку "Удалить"
    И админ кликает кнопку "Удалить"
    Тогда админ не видит в таблице "<название группы>"
    Примеры:
      | название группы |
      | ласковый_май    |

  @sync_user
  Структура сценария: Пользователь успешно синхронизируется при выборе каталога
    Дано пользователь "<логин пользователя>" отсутствует в списке при отключении синхронизации каталогов "<список каталогов>"
    Когда админ включает синхронизацию по каталогам "<список каталогов>"
    И админ добавляет фильтр "По логину" со значением "<логин пользователя>"
    Тогда админ видит в таблице "<логин пользователя>"
    Примеры:
      | логин пользователя | список каталогов |
      | printserver-admin  | Администраторы, pg-test-group2, pg-test-group3 |

