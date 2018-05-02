import {BrowserModule} from '@angular/platform-browser';
import {NgModule, Provider} from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from '@angular/forms'; // <-- NgModel lives here
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {FlexLayoutModule} from '@angular/flex-layout';

import {MatInputModule} from '@angular/material/input';
import {
  MatButtonModule, MatCardModule, MatIconModule, MatListModule, MatRadioModule, MatSidenavModule,
  MatToolbarModule
} from '@angular/material';

import {AppComponent} from './app.component';
import {NoteComponent} from './component/note/note.component';
import {NoteContentComponent} from './component/note/note-content/note-content.component';
import {InViewportModule, WindowRef} from '@thisissoon/angular-inviewport';
import {ScrollSpyModule} from '@thisissoon/angular-scrollspy';
import {Ng2PageScrollModule} from 'ng2-page-scroll';
import {AppRoutingModule} from './app-routing.module';
import {NoteMiniComponent} from './component/note/note-mini/note-mini.component';
import {NoteService} from './service/note.service';
import {GlobalGuard} from './guards/global.guard';
import {AuthenticationService} from './service/authentication.service';
import {DashboardComponent} from './component/dashboard/dashboard.component';
import {NavBarComponent} from './component/nav-bar/nav-bar.component';
import {LoginFormComponent} from './component/login-form/login-form.component';
import {JwtRequestInterceptor} from './interceptor/jwt-request-interceptor';
import {FroalaEditorModule, FroalaViewModule} from 'angular-froala-wysiwyg';
import {NoteEditorComponent} from './component/note-editor/note-editor.component';
import {HighlightJsModule, HighlightJsService} from 'angular2-highlight-js';
import {CodeHighlightService} from './service/code-highlight.service';
import {NotesPageComponent} from './component/notes-page/notes-page.component';
import {NotesSectionComponent} from './component/notes-page/notes-section/notes-section.component';
import {AllItemsComponent} from './component/all-items/all-items.component';
import {NewNoteFormComponent} from './component/notes-page/new-note-form/new-note-form.component';
import {ListEditorComponent} from './component/note/list-editor/list-editor.component';
import {LocalStorageService} from './service/local-storage.service';
import { ProfilePageComponent } from './component/profile-page/profile-page.component';
import {ProfileService} from "./service/profile.service";
import { ProfilDetailsComponent } from './component/profile-page/profil-details/profil-details.component';
import { FriendsComponent } from './component/profile-page/friends/friends.component';
import { UserMiniComponent } from './component/user-mini/user-mini.component';

// Provide window object for browser and a suitable replacement
// on other platforms
const getWindow = () => window;
const providers: Provider[] = [
  {provide: WindowRef, useFactory: (getWindow)},
];

@NgModule({
  declarations: [
    AppComponent,
    LoginFormComponent,
    DashboardComponent,
    NoteComponent,
    NavBarComponent,
    NoteContentComponent,
    NoteMiniComponent,
    NoteEditorComponent,
    NotesPageComponent,
    NotesSectionComponent,
    AllItemsComponent,
    NewNoteFormComponent,
    ListEditorComponent,
    ProfilePageComponent,
    ProfilDetailsComponent,
    FriendsComponent,
    UserMiniComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatListModule,
    MatToolbarModule,
    MatSidenavModule,
    MatButtonModule,
    MatRadioModule,
    MatIconModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    MatCardModule,
    InViewportModule.forRoot(providers),
    ScrollSpyModule.forRoot(),
    Ng2PageScrollModule,
    AppRoutingModule,
    FroalaEditorModule.forRoot(),
    FroalaViewModule.forRoot(),
    HighlightJsModule
  ],
  providers: [
    NoteService,
    LocalStorageService,
    ProfileService,
    GlobalGuard,
    AuthenticationService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtRequestInterceptor,
      multi: true
    },
    HighlightJsService,
    CodeHighlightService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
