import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  startTimer = new Date();
  event = new Date(2020, 11, 25, 11, 59, 0o0);

  Months = Math.abs(this.event.getMonth() - (this.startTimer.getMonth() + 1));
  Days = Math.abs(this.event.getDate() - this.startTimer.getDate());
  Hours = Math.abs(this.event.getHours() - this.startTimer.getHours());
  Minutes = Math.abs(this.event.getMinutes() - this.startTimer.getMinutes());
  Seconds = Math.abs(this.event.getSeconds() - this.startTimer.getSeconds());

}



