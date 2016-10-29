import { Component } from '@angular/core';
@Component({
  selector: 'my-app',
  template: `
   
    <h1>{{title}}</h1>
    
    <div><h1>My First Angular App</h1></div>
    
     <nav>
      <a routerLink="/cats" routerLinkActive="active">Cats</a>  &nbsp;&nbsp; | &nbsp;&nbsp;
      <a routerLink="/dogs" routerLinkActive="active">Dogs</a>
    </nav>
    
    <!-- Router Outlet -->
    <router-outlet></router-outlet>
    
   `
})
export class AppComponent {
   title = 'My Title';
 }