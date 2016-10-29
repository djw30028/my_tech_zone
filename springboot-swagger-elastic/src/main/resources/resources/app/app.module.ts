// Imports
import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

// Declarations
import { AppComponent }         from './app.component';
import { CatListComponent }     from './cats/cat-list.component';
import { DogListComponent }     from './dogs/dog-list.component';

import { AppRoutingModule }     from './app.routes';
import { PetService }           from './pets.service';

@NgModule({
  imports:      [ 
    BrowserModule, 
    AppRoutingModule 
  ],
  declarations: [ 
     AppComponent,
     CatListComponent,
     DogListComponent ],
  providers: [
    PetService
  ],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }