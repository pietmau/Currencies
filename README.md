## MVVM
- The View (`CurrenciesFragment`) binds to `RxCurrenciesViewModel` via LiveData.

- The ViewModel communicates with the UseCase (`RxGetCurrenciesUseCase`) via RxJava.

The reason of this mixture is that it would be interesting to implement the ViewModel with Coroutines (TODO).
Having the presentation not relying on RxJava would allow us to do so.

### The UseCase (RxGetCurrenciesUseCase)
Based on RxJava.

Every second it queries the repository with the symbol of the currency received from the user.

It implements retry.

It is retained across config changes via `ViewModel` (Architecture Components).

## Modularity & Clean Architecture
The project is composed of three modules:
- **Presentation** (Android application)
- **Data** (Android Library)
- **Domain** (Java library)

**Presentation** depends on **Data** and **Domain** (transitively via Data)

**Data** depends on **Domain**

**Domain** does not depend on any other module.

## Tests
There are unit tests (**Junit 5**) and **Espresso** tests in the respective directories:
- some unit tests in all modules, in the respective directories;
- some Espresso test in **presentation**

## Dagger & Tests
In order to run the Espresso test, we inject a `TestAppComponent` that provides
the test dependencies.

## The RecyclerView
The `CurrenciesRecycler` uses `DiffUtils` and `Payloads` to update the rows efficiently.

## Other libraries
- Mockk
- ViewModel
- LiveData
- LeakCanary
- Retrofit
- Dagger
- AndroidX

## Currency
Money is represented with `BigDecimal`.

## TODO
- Explain things a bit better (especially around RxJava).
- Use coroutines instead or RxJava.
- Use a bound service to get the rates.
- Repository is not really the right name, should find something better.
- Use a retrofit converter.
- Add a few more tests.