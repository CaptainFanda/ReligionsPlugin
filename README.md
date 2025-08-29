# ReligionsPlugin

Это плагин добавляющий настоящую систему религий, возможно

# Команды

/religion - Посмотреть какая у вас религия

/religion god - Посмотреть бога вашей религии

/religion clear - Стать атеистом

/religion get <религия> - Принять религию за ресурсы установленные в religions.yml

/religion members - Посмотреть кол-во последователей вашей религии

// Админ команды

/religion set <игрок> <религия> - Установить религию игроку

/religion check <игрок> - посмотреть религию игрока

# Пермишионы

ReligionPlugin.set

ReligionPlugin.check

# Плейсхолдеры

%ReligionPlugin_religion% - религия игрока

%ReligionPlugin_members% - Кол-во последователей религии игрока

%ReligionPlugin_god% - Бог религии игрока

# Базы Данных

В config.yml есть настройка баз данных, Для указания субд нужно их вписать в значние dbms, Доступные субд: MySql, sqlite

Для sqlite ничего не надо настраивать, для MySql настройке секцию database
