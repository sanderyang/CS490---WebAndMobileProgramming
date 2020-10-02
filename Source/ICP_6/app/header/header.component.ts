import { Component } from '@angular/core';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styles: [
    '.background {background: cadetblue; color: white}',
    'li a { color: white}',
    'ul.nav a:hover { color: crimson  }',
  ]
})
export class HeaderComponent {
  constructor() {}

  }
