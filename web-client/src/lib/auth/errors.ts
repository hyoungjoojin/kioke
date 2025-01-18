import { CredentialsSignin } from "next-auth";

export class AuthServiceNotAvailableError extends CredentialsSignin {}
export class AuthServiceInternalServerError extends CredentialsSignin {}
export class AuthLoginInvalidCredentialsError extends CredentialsSignin {}
