import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-search-recipe',
  templateUrl: './search-recipe.component.html',
  styleUrls: ['./search-recipe.component.css']
})
export class SearchRecipeComponent implements OnInit {
  @ViewChild('recipe') recipes: ElementRef;
  @ViewChild('place') places: ElementRef;
  recipeValue: any;
  placeValue: any;
  venueList: any;
  recipeList: any;

  currentLat: any;
  currentLong: any;
  geolocationPosition: any;

  constructor(private _http: HttpClient) {
  }

  ngOnInit() {

    window.navigator.geolocation.getCurrentPosition(
      position => {
        this.geolocationPosition = position;
        this.currentLat = position.coords.latitude;
        this.currentLong = position.coords.longitude;
      });
  }

  getVenues() {

    this.recipeValue = this.recipes.nativeElement.value;
    this.placeValue = this.places.nativeElement.value;


    // tslint:disable-next-line:max-line-length
    if (this.recipeValue !== null) {
      // tslint:disable-next-line:max-line-length
      this._http.get('https://api.edamam.com/search?app_id=f50d2f9c&app_key=4301ac3ad3c43df2f179b0f87ac9ef05&from=0&to=5&q=' + this.recipeValue)
        .subscribe((responses: any) => {
          this.recipeList = Object.keys(responses.hits).map(function (k) {
            var i = responses.hits[k];
            return {url: i.recipe.url, title: i.recipe.label, icon: i.recipe.image};
          });
        });
    }

    if (this.placeValue != null && this.placeValue !== '' && this.recipeValue != null && this.recipeValue !== '') {
      // tslint:disable-next-line:max-line-length
      this._http.get('https://api.foursquare.com/v2/venues/search?client_id=NMYT3TVAO2Q1LFL2USNECMB2QQQ3XDAGWAAFTXL4HMYGCAQS&client_secret=I1T2IFEPSGK' +
        'KMN5BFM0WDKF3QW4XMKQFU1X5EIOJZBDE3FOK&near=' + this.placeValue + '&query=' + this.recipeValue + '&limit=5&v=20200101')
        .subscribe((responses: any) => {
          this.venueList = Object.keys(responses.response.venues).map(function (k) {
            var i = responses.response.venues[k];
            return {name: i.name, address: i.location};
          });
        });
    }
  }
}
