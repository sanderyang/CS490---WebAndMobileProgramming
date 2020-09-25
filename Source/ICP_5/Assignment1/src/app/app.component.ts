import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  // define list of items
  items = [];

  // Pushing new Item to the To Do List
  submitNewItem(item) {
    this.items.push(item);
  }

  // Deleting Item from the To Do List
  deleteItem(item) {
    for (let i = 0; i <= this.items.length; i++) {
      if ( item === this.items[i]) {
        this.items.splice(i, 1);
      }
    }
  }
}
