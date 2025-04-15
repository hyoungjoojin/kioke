export interface GetMyInformationResponseBody {
  userId: string;
  email: string;
  firstName: string;
  lastName: string;
  preferences: {
    locale: string;
    theme: string;
  };
}

export interface GetUserResponseBody {
  userId: string;
  email: string;
  firstName: string;
  lastName: string;
}

export interface SearchUserResponseBody {
  userId: string;
  email: string;
  firstName: string;
  lastName: string;
}
