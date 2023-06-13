# AdCollector
This is a simple Ads app. Showcasing image, price, title and location of ads in a grid.
Has a searchBar: Search functionality is very limited becaouse of time restriction.
Also has a toggle favourites button at the end of this bar, illustrated by star. This toggle sorts all favourites that are stored locally in a ROOM database. When toggled off it will just get all ads from API again.

User manual:
- Users can add Ad to favourites locally by clicking on ad Image, and a star will showcase this with either being full or partial (not favourite). 
- Toggle to only show favourites by clicking the star at the end of the search bar.
- Search for titles (VERY limited search)
- If user clicks "X" when typing, the app will get all the Ads again. 

Generally:
- Navigation: The app i fully compose, with compose NavHost Navigation and routes. Simple BottomNavigation: "Ads" is home page. 
- Dependency injection: Using Koin and separating conserts by modules. 
- Modules: Main, Feature-ads, Library-style, Library-repositories

Improvements and thoughts:
- If i had time i would use a pager to get that amount of ads, to take care of scrolling performance. 
- Would use a flow state to be able to respond to new ads coming in and refresh the state/ads.
- Would store which toggle favourite state the user last used in a Datastore locally, to remember at start up.
- Fix add favourite bugs and quirks, and smoother toggle favourites: 
- Implement Add delete/unfavourite Ad to ROOM database
- Would add "Ad screen" navigation to be able to go to the ad for more information. 
- Add tests, viewModel and UI
- Alot more error handling and check: Like, if favourite is already stored, dont do anything.
- Move AdViewmodel to its own screen only and not injected in Main, where it does not belong (this is because of new to Compose NavHost, and didnt have time to solve/separate the crash when viewModel was injected in AdsScreen).
- Better API handling, PUT/POST/GET 

Enjoyed working with this app, with the time I had. 
