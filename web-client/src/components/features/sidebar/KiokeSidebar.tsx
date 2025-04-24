"use client";

import { Button } from "@/components/ui/button";
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarGroup,
  SidebarGroupLabel,
  SidebarHeader,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
  useSidebar,
} from "@/components/ui/sidebar";
import { KIOKE_ROUTES } from "@/constants/route";
import { useShelvesQuery } from "@/hooks/query/shelf";
import { useCurrentView } from "@/hooks/store/view";
import {
  AlignJustify,
  Bell,
  ChevronDown,
  HeartIcon,
  HomeIcon,
  Library,
  LogOut,
  Settings,
} from "lucide-react";
import View from "@/constants/view";
import { User } from "next-auth";
import { useRouter } from "next/navigation";
import {
  Collapsible,
  CollapsibleContent,
  CollapsibleTrigger,
} from "@/components/ui/collapsible";
import { cn } from "@/lib/utils";
import { useState } from "react";
import { Skeleton } from "@/components/ui/skeleton";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { signOut } from "next-auth/react";
import Link from "next/link";
import { useSelectedShelfId } from "@/hooks/store/shelf";

interface ProfileButtonProps {
  firstName: string;
  lastName: string;
}

function ProfileButton({ firstName, lastName }: ProfileButtonProps) {
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

export default function KiokeSidebar({ user }: { user: User }) {
  const router = useRouter();

  const { data: shelves, isLoading } = useShelvesQuery();
  const { selectedShelfId } = useSelectedShelfId();

  const { setOpen } = useSidebar();
  const [showShelves, setShowShelves] = useState(true);

  const { currentView, setCurrentView } = useCurrentView();

  const sidebarMenuItems = [
    {
      label: "Home",
      icon: <HomeIcon />,
      view: View.HOME,
    },
    {
      label: "Bookmarks",
      icon: <HeartIcon />,
      view: View.BOOKMARKS,
    },
  ];

  return (
    <Sidebar className="pt-3 pl-3">
      <SidebarHeader className="mb-5 pr-1">
        <div className="w-full flex justify-between items-center">
          <ProfileButton firstName={user.firstName} lastName={user.lastName} />

          <div>
            <Button variant="ghost" className="hover:cursor-not-allowed">
              <Bell size={20} />
            </Button>
            <Button
              variant="ghost"
              onClick={() => {
                setOpen(false);
              }}
            >
              <AlignJustify size={20} />
            </Button>
          </div>
        </div>
      </SidebarHeader>

      <SidebarContent className="pr-3">
        <SidebarGroup className="mb-5">
          <SidebarMenu>
            {sidebarMenuItems.map((sidebarMenuItem, index) => {
              return (
                <SidebarMenuItem key={index}>
                  <SidebarMenuButton asChild>
                    <Button
                      variant="ghost"
                      className={cn(
                        "justify-start",
                        currentView === sidebarMenuItem.view
                          ? "bg-gray-200 hover:bg-gray-200"
                          : "",
                      )}
                      onClick={() => {
                        if (currentView === sidebarMenuItem.view) {
                          return;
                        }

                        router.push(KIOKE_ROUTES[sidebarMenuItem.view]());
                        setCurrentView(sidebarMenuItem.view);
                      }}
                    >
                      {sidebarMenuItem.icon}
                      <span>{sidebarMenuItem.label}</span>
                    </Button>
                  </SidebarMenuButton>
                </SidebarMenuItem>
              );
            })}
          </SidebarMenu>
        </SidebarGroup>

        <Collapsible
          defaultOpen
          onOpenChange={(open) => {
            setShowShelves(open);
          }}
          className="group/collapsible"
        >
          <SidebarGroup>
            <SidebarGroupLabel asChild>
              <CollapsibleTrigger>
                Shelves
                <ChevronDown className="ml-auto transition-transform group-data-[state=open]/collapsible:rotate-180" />
              </CollapsibleTrigger>
            </SidebarGroupLabel>

            <CollapsibleContent>
              {showShelves && (
                <SidebarGroup>
                  <SidebarMenu>
                    {isLoading ? (
                      <>
                        <Skeleton className="w-full h-9" />
                        <Skeleton className="w-full h-9" />
                        <Skeleton className="w-full h-9" />
                      </>
                    ) : (
                      shelves &&
                      shelves.map((shelf, index) => {
                        return (
                          <SidebarMenuItem key={index}>
                            <SidebarMenuButton asChild>
                              <Button
                                variant="ghost"
                                className={cn(
                                  "justify-start",
                                  currentView === View.SHELF &&
                                    shelf.shelfId === selectedShelfId
                                    ? "bg-gray-200 hover:bg-gray-200"
                                    : "",
                                )}
                                onClick={() => {
                                  router.push(
                                    KIOKE_ROUTES[View.SHELF](shelf.shelfId),
                                  );
                                }}
                              >
                                <Library />
                                <span>{shelf.name}</span>
                              </Button>
                            </SidebarMenuButton>
                          </SidebarMenuItem>
                        );
                      })
                    )}
                  </SidebarMenu>
                </SidebarGroup>
              )}
            </CollapsibleContent>
          </SidebarGroup>
        </Collapsible>
      </SidebarContent>
      <SidebarFooter />
    </Sidebar>
  );
}
