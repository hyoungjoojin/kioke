'use client';

import { Button } from '@/components/ui/button';
import Modal from '@/components/ui/modal';
import {
  Sidebar,
  SidebarContent,
  SidebarGroup,
  SidebarGroupContent,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
  SidebarProvider,
} from '@/components/ui/sidebar';
import { Paintbrush, Trash, TriangleAlert } from 'lucide-react';
import { useState } from 'react';

export default function SettingsModal() {
  const dangerZoneContent = (
    <div>
      <h1 className='font-bold'>Delete account</h1>
      <p>This will delete your account.</p>
      <Button variant='destructive'>
        <Trash />
        Delete account
      </Button>
    </div>
  );

  const menu = [
    { name: 'Appearance', icon: Paintbrush, content: <></> },
    { name: 'Danger Zone', icon: TriangleAlert, content: dangerZoneContent },
  ];

  const [menuIndex, setMenuIndex] = useState(0);

  return (
    <Modal title='Settings'>
      <SidebarProvider>
        <Sidebar collapsible='none' className='bg-white w-52'>
          <SidebarContent>
            <SidebarGroup>
              <SidebarGroupContent>
                <SidebarMenu>
                  {menu.map((item, index) => {
                    return (
                      <SidebarMenuItem
                        key={item.name}
                        onClick={() => {
                          setMenuIndex(index);
                        }}
                      >
                        <SidebarMenuButton
                          asChild
                          isActive={index === menuIndex}
                        >
                          <a href='#'>
                            <item.icon />
                            <span>{item.name}</span>
                          </a>
                        </SidebarMenuButton>
                      </SidebarMenuItem>
                    );
                  })}
                </SidebarMenu>
              </SidebarGroupContent>
            </SidebarGroup>
          </SidebarContent>
        </Sidebar>
        <div>{menu.at(menuIndex)?.content}</div>
      </SidebarProvider>
    </Modal>
  );
}
