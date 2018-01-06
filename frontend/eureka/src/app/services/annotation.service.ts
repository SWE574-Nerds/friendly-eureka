import { Injectable } from '@angular/core';
import { User } from './user';
import { UserService } from './user.service';
import { Headers, Http } from '@angular/http';
import { Router } from '@angular/router';
import { Card } from '../components/cardview/card';
import { Listory } from './Listory';
import { Annotation } from './Annotation';
import { AnnotationSelector } from './AnnotationSelector';
import { AnnotationCreateResult } from './AnnotationCreateResult';


import 'rxjs/add/operator/toPromise';

@Injectable()
export class AnnotationService {
  private listoryUrl = '/api/listory';
  private listoryIdUrl = '/api/listory/:id/annotations';

  private textAnnotationURL = '/api/annotation/:listoryId/text';
  private highlightAnnotationURL = '/api/annotation/:listoryId/highlight';
  private imageAnnotationURL = '/api/annotation/:listoryId/image';

  constructor(
    private http: Http,
    private userService: UserService
  ) { }

  getAnnotationsOfListory(id): Promise<Annotation[]> {
    return new Promise<Annotation[]>(resolve => {
      this.http.get(this.listoryIdUrl.replace(':id', id))
        .toPromise()
        .then((resp) => {
          var listory: Annotation[] = resp.json() as Annotation[];
          resolve(listory);
        });
    });
  }

  getAnnotationOwner(ownerIRI): Promise<object> {
    const userId = ownerIRI.replace(/.+user\/?/g, "");
    return new Promise<object>(resolve => {
      this.userService.getUserInfo(userId).then((user) => { resolve(user); });
    });
  }

  createAnnotation(selector: AnnotationSelector): Promise<AnnotationCreateResult > {

    return new Promise<AnnotationCreateResult>((resolve) => {
      this.userService.getUser().then((user) => {

        if (selector.highlight) {
          var body_highlight = {
            listory: selector.listoryId,
            body: {
              message: ""
            }
          };

          this.http.post(this.highlightAnnotationURL.replace(":listoryId", selector.listoryId), body_highlight, {
            headers: this.createHeaders(user.token)
          })
            .toPromise()
            .then((resp) => {
              var result = new AnnotationCreateResult();
              resolve(result);
            });
        } else if (selector.mediaType === "text") {
          var body_text = {
            listory: selector.listoryId,
            body: {
              message: selector.description
            },
            selector: null
          };

          if (selector.textSelector)
          {
            body_text.selector = {
              text: {
                startsWith: selector.startsWith,
                endsWith: selector.endsWith,
                selection: selector.selection
              }
            };
          }

          this.http.post(this.textAnnotationURL.replace(":listoryId", selector.listoryId), body_text, {
            headers: this.createHeaders(user.token)
          })
            .toPromise()
            .then((resp) => {
              var result = new AnnotationCreateResult();
              resolve(result);
            });
        } else if (selector.mediaType === "image") {
          var body_image = {
            listory: selector.listoryId,
            body: {
              message: selector.description,
              link: selector.link
            },
            selector: null
          };


          if (selector.imageSelector)
          {
            body_image.selector = {
              image: {
                imgLink: selector.startsWith
              }
            };
          }

          this.http.post(this.imageAnnotationURL.replace(":listoryId", selector.listoryId), body_image, {
            headers: this.createHeaders(user.token)
          })
            .toPromise()
            .then((resp) => {
              var result = new AnnotationCreateResult();
              resolve(result);
            });
        }
      });
    });
  }

  private createHeaders(token: string): Headers {
    var headers = new Headers({ 'Content-Type': 'application/json' });
    if (token)
      headers.append('Authorization', token);

    return headers;
  }

}
