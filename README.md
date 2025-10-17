# Rick and Morty Characters App

## 📱 Описание проекта

Android приложение для просмотра персонажей из популярного мультсериала "Rick and Morty". Приложение использует официальное API Rick and Morty для получения данных о персонажах и предоставляет удобный интерфейс для их просмотра с возможностью фильтрации и поиска.

<img width="213" height="450" alt="download" src="https://github.com/user-attachments/assets/0652b4b5-65cc-43ec-952e-5db9a0d9bf83" />


## ✨ Основные возможности

- **Просмотр персонажей** - отображение списка всех персонажей в виде карточек
- **Поиск персонажей** - поиск по имени персонажа
- **Фильтрация** - фильтрация по статусу (живой/мертвый/неизвестно), виду и полу
- **Детальная информация** - просмотр подробной информации о каждом персонаже
- **Пагинация** - автоматическая подгрузка новых персонажей при прокрутке
- **Pull-to-refresh** - обновление данных потягиванием вниз
- **Офлайн кэширование** - сохранение данных в локальной базе данных

## 🏗️ Архитектура

Приложение построено с использованием современной архитектуры Android:

- **MVVM (Model-View-ViewModel)** - разделение логики и UI
- **Jetpack Compose** - современный UI фреймворк
- **Hilt** - dependency injection
- **Repository Pattern** - абстракция источника данных
- **Room Database** - локальное хранение данных
- **Retrofit** - сетевое взаимодействие
- **Paging 3** - эффективная пагинация

## 🛠️ Технологический стек

### Основные технологии:
- **Kotlin** - основной язык программирования
- **Jetpack Compose** - декларативный UI
- **Material Design 3** - дизайн система

### Сетевые технологии:
- **Retrofit 2.11.0** - HTTP клиент
- **OkHttp 4.12.0** - сетевая библиотека
- **Gson 2.11.0** - JSON парсинг

### База данных:
- **Room 2.6.1** - локальная база данных
- **Room Paging** - пагинация в базе данных

### Dependency Injection:
- **Hilt 2.48** - DI фреймворк

### UI компоненты:
- **Navigation Compose 2.8.4** - навигация
- **Paging Compose 3.3.2** - пагинация в UI
- **Coil 2.7.0** - загрузка изображений
- **Material 3** - компоненты UI

## 📁 Структура проекта

```
app/src/main/java/com/example/testrikandmorty/
├── data/
│   ├── api/                    # API интерфейсы
│   │   └── RickAndMortyApi.kt
│   ├── database/               # Room база данных
│   │   ├── AppDatabase.kt
│   │   ├── CharacterDao.kt
│   │   └── Converters.kt
│   ├── model/                  # Модели данных
│   │   └── Character.kt
│   ├── paging/                 # Пагинация
│   │   └── CharacterPagingSource.kt
│   └── repository/             # Репозиторий
│       └── CharacterRepository.kt
├── di/                         # Dependency Injection
│   └── AppModule.kt
├── navigation/                 # Навигация
│   └── RickAndMortyNavigation.kt
├── ui/
│   ├── components/             # UI компоненты
│   │   ├── CharacterCard.kt
│   │   └── FilterBottomSheet.kt
│   ├── screen/                 # Экраны
│   │   ├── CharactersScreen.kt
│   │   └── CharacterDetailScreen.kt
│   ├── theme/                  # Тема приложения
│   │   └── Theme.kt
│   └── viewmodel/              # ViewModels
│       ├── CharactersViewModel.kt
│       └── CharacterDetailViewModel.kt
├── MainActivity.kt
└── RickAndMortyApplication.kt
```

## 🚀 Установка и запуск

### Требования:
- Android Studio Hedgehog или новее
- Android SDK 29+ (API Level 29)
- Kotlin 2.0.21+

### Шаги установки:

1. **Клонирование репозитория:**
   ```bash
   git clone <repository-url>
   cd TestRikAndMorty
   ```

2. **Открытие в Android Studio:**
   - Откройте Android Studio
   - Выберите "Open an existing project"
   - Выберите папку проекта

3. **Синхронизация проекта:**
   - Android Studio автоматически синхронизирует Gradle файлы
   - Дождитесь завершения синхронизации

4. **Сборка проекта:**
   ```bash
   ./gradlew assembleDebug
   ```

5. **Установка на устройство:**
   ```bash
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

## 📱 Использование приложения

### Главный экран:
- Отображает сетку персонажей в виде карточек
- Каждая карточка содержит: изображение, имя, вид, статус и пол
- Поддержка pull-to-refresh для обновления данных

### Поиск и фильтрация:
- **Поиск**: нажмите на иконку поиска и введите имя персонажа
- **Фильтры**: нажмите на иконку настроек для открытия панели фильтров
  - Статус: Alive, Dead, Unknown
  - Вид: Human, Alien, Humanoid, и др.
  - Пол: Female, Male, Genderless, Unknown

### Детальная информация:
- Нажмите на карточку персонажа для просмотра подробной информации
- Отображается полная информация о персонаже включая происхождение и локацию

## 🔧 Конфигурация

### API Endpoint:
Приложение использует официальное API Rick and Morty:
```
https://rickandmortyapi.com/api/
```

### Разрешения:
- `INTERNET` - для сетевых запросов к API

## 🎨 Дизайн

- **Material Design 3** - современный дизайн
- **Адаптивная сетка** - автоматическое изменение количества колонок
- **Темная/светлая тема** - поддержка системных настроек
- **Анимации** - плавные переходы между экранами

## 🧪 Тестирование

### Запуск тестов:
```bash
# Unit тесты
./gradlew test

# Instrumented тесты
./gradlew connectedAndroidTest
```

## 📦 Сборка релизной версии

```bash
# Сборка release APK
./gradlew assembleRelease

# Сборка AAB для Google Play
./gradlew bundleRelease
```

## 🤝 Вклад в проект

1. Fork репозитория
2. Создайте feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit изменения (`git commit -m 'Add some AmazingFeature'`)
4. Push в branch (`git push origin feature/AmazingFeature`)
5. Откройте Pull Request

## 📄 Лицензия

Этот проект распространяется под лицензией MIT. См. файл `LICENSE` для подробностей.

## 👨‍💻 Автор

Создано как тестовое задание для демонстрации навыков Android разработки.

## 🔗 Полезные ссылки

- [Rick and Morty API Documentation](https://rickandmortyapi.com/documentation)
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Hilt Documentation](https://dagger.dev/hilt/)
- [Room Database Guide](https://developer.android.com/training/data-storage/room)

---

**Примечание**: Это приложение создано исключительно в образовательных целях и использует публичное API Rick and Morty. Все права на персонажей принадлежат их создателям.
