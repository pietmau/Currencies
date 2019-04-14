
## MVVM
The View(the `CurrenciesFragment`) binds to `RxCurrenciesViewModel` via LiveData.

The ViewModel communicates with the use case with RxJava.

The reason of this unusual mixture is that it would be interesting to have an implementation of the ViewModel with Coroutines (TODO). 

### The UseCase (RxGetCurrenciesUseCase)
Based on RxJava.
Every second queries the repository with the symbol of the currency receiveb from the user.
Implements retry.


## Tests
There are Unit tests (Junit 5) and Espresso tests in the respective directories.


## TODO

- COMMENTS!!!
- coroutines
- USE A SERVICE!
- Fix edditezt to get only valid numbers
- bettern aname for reposoiyty
- use retrofit converter
- colori!
- more test