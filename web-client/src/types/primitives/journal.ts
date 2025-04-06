import { Role } from "@/constants/role";
import { PagePreview } from "./page";

export interface Journal {
  id: string;
  title: string;
  description: string;
  bookmarked: boolean;
  createdAt: string;
  lastModified: string;
  users: {
    userId: string;
    role: Role;
    email: string;
    firstName: string;
    lastName: string;
  }[];
  pages: PagePreview[];
}

export interface JournalPreview {
  journalId: string;
  title: string;
  bookmarked: boolean;
  createdAt: string;
}
