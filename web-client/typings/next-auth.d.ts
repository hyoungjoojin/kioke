import type { AdapterUser, Session, User } from 'next-auth';
import 'next-auth/jwt';

declare module 'next-auth' {
  export interface User {
    uid: string;
    email: string;
    firstName: string;
    lastName: string;
    accessToken: string;
  }

  export interface Session {
    accessToken: string;
  }
}

declare module 'next-auth/jwt' {
  export interface JWT {
    user: User & AdapterUser;
    accessToken: string;
  }
}
