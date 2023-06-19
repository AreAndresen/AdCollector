# AdCollector
This is a simple Ads app. Showcasing image, price, title and location of ads in a grid.
Has a search bar: Search functionality is very limited because of time restrictions.
Also has a toggle favourites button at the end of this bar, illustrated by a star. This toggle sorts all favourites that are stored locally in a ROOM database. When toggled off it will just get all ads from API again.

User manual:
- Users can add or remove Ads to favourites locally by clicking on the ad Image, and a star will showcase this with either being full or partial (not favourite). 
- Toggle to only show favourites by clicking the star at the end of the search bar.
- Search for titles (VERY limited search)
- If the user clicks "X" when typing, the app will get all the Ads again. 

Generally:
- Navigation: The app is fully compose, with NavHost Navigation and routes. Simple BottomNavigation: "Ads" is the home page. 
- Dependency injection: Using Koin and separating concerns by modules. 
- Modules: Main, Feature-ads, Library-style, Library-repositories

Improvements and thoughts:
- If i had time i would use a pager to get that amount of ads, to take care of scrolling performance. 
- Would use a flow state to be able to respond to new ads coming in and refresh the state/ads.
- Would store which toggle favourite state the user last used in a Datastore locally, to remember at start up.
- Would add "Ad screen" navigation to be able to go to the ad for more information. 
- Add tests, viewModel and UI
- A lot more error handling and checking: Like, if the ad is already stored as favourite, then dont do anything.
- Better API handling, PUT/POST/GET 

Enjoyed working with this app, with the time I had. 
