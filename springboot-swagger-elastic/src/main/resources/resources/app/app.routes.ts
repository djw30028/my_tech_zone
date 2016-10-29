import { NgModule }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CatListComponent } from './cats/cat-list.component';
import { DogListComponent } from './dogs/dog-list.component';

// Route Configuration
export const routes: Routes = [
  {
    path: '', component: CatListComponent // Remember to import the Home Component
  },
  { path: 'cats', component: CatListComponent },
  { path: 'dogs', component: DogListComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})

export class AppRoutingModule {}