"use server";

import { signIn } from "./";

export async function signInWithCredentials(credentials: any) {
  return await signIn("credentials", credentials);
}
