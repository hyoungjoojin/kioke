"use client";

import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { ChevronDown, LogOut, Settings } from "lucide-react";
import { signOut } from "next-auth/react";
import Link from "next/link";

interface ProfileButtonProps {
  firstName: string;
  lastName: string;
}

export default function ProfileButton({
  firstName,
  lastName,
}: ProfileButtonProps) {
  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Button variant="ghost">
          <div className="flex items-center">
            <Avatar>
              <AvatarFallback>{`${firstName[0]}${lastName[0]}`}</AvatarFallback>
            </Avatar>

            <p className="text-sm">{firstName}</p>

            <ChevronDown size={16} />
          </div>
        </Button>
      </DropdownMenuTrigger>

      <DropdownMenuContent loop align="start" sideOffset={10}>
        <Link href="/settings">
          <DropdownMenuItem>
            <div className="flex items-center">
              <Settings className="h-4 w-4 mr-1" />
              Settings
            </div>
          </DropdownMenuItem>
        </Link>
        <DropdownMenuSeparator />

        <DropdownMenuItem
          onClick={() => signOut({ redirectTo: "/", redirect: true })}
        >
          <div className="flex items-center">
            <LogOut className="h-4 w-4 mr-1" />
            Log out
          </div>
        </DropdownMenuItem>
      </DropdownMenuContent>
    </DropdownMenu>
  );
}
