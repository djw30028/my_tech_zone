// Imports
import { Injectable }    from '@angular/core';
import { Jsonp, URLSearchParams } from '@angular/http';

import { Pet } from './pet';
import { PETS } from './mock-pets';

// Decorator to tell Angular that this class can be injected as a service to another class
@Injectable()
export class PetService {
   getGogs(): Promise<Pet[]> {
      return Promise.resolve(PETS);
   }
}