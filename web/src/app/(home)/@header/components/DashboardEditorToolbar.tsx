import Widgets from '@/components/feature/dashboard/widgets';
import { Button } from '@/components/ui/button';
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from '@/components/ui/popover';
import { Separator } from '@/components/ui/separator';
import { DashboardWidgetType } from '@/constant/dashboard';
import { cn } from '@/lib/utils';
import { useUpdateDashboardMutation } from '@/query/dashboard/updateDashboard';
import { useDashboard, useDashboardActions } from '@/store/dashboard';
import { useTranslations } from 'next-intl';
import { useState } from 'react';
import { v4 as uuidv4 } from 'uuid';

export default function DashboardEditorToolbar() {
  const t = useTranslations();

  const [selectedWidget, setSelectedWidget] = useState<{
    type: DashboardWidgetType;
    group: string;
    tag: string;
  } | null>(null);

  const { draft } = useDashboard();
  const { updateDraft } = useDashboardActions();
  const { mutate: updateDashboard, isPending: isUpdateDashboardPending } =
    useUpdateDashboardMutation();

  const handleInsertWidgetButtonClick = async () => {
    if (!selectedWidget) {
      return;
    }

    const id = uuidv4().toString();
    const content = await Widgets[selectedWidget.type].default();

    updateDraft((current) => {
      return {
        widgets: [
          ...current.widgets,
          {
            id,
            type: selectedWidget.type,
            x: 0,
            y: 0,
            content,
          },
        ],
      };
    });
  };

  const handleSaveButtonClick = () => {
    if (draft) {
      updateDashboard({
        widgets: draft.widgets,
      });
    }
  };

  return (
    <div className='flex justify-between'>
      <div className='flex gap-2'>
        {widgetGroups.map((group, index) => {
          return (
            <Popover key={index}>
              <PopoverTrigger asChild>
                <Button variant='ghost'>
                  {t(`header.edit-layout.groups.${group.name}.name`)}
                </Button>
              </PopoverTrigger>
              <PopoverContent
                align={
                  index > (widgetGroups.length * 2) / 3
                    ? 'end'
                    : index > widgetGroups.length / 3
                      ? 'center'
                      : 'start'
                }
                className='w-[32rem] h-96'
              >
                <div className='flex h-full'>
                  <div>
                    {group.widgets.map((widget, index) => {
                      return (
                        <div
                          className={cn(
                            'px-2 rounded-3xl text-center',
                            'hover:cursor-pointer',
                            selectedWidget &&
                              selectedWidget.type === widget.type &&
                              'bg-accent',
                          )}
                          key={index}
                          onClick={() => {
                            setSelectedWidget({
                              type: widget.type,
                              group: group.name,
                              tag: widget.name,
                            });
                          }}
                        >
                          {t(
                            `header.edit-layout.groups.${group.name}.${widget.name}.name`,
                          )}
                        </div>
                      );
                    })}
                  </div>

                  <Separator orientation='vertical' className='mx-1' />

                  <div className='h-full flex flex-col grow px-3'>
                    {selectedWidget && (
                      <>
                        <h1>
                          {t(
                            `header.edit-layout.groups.${selectedWidget.group}.${selectedWidget.tag}.title`,
                          )}
                        </h1>
                        <p className='text-xs'>
                          {t(
                            `header.edit-layout.groups.${selectedWidget.group}.${selectedWidget.tag}.description`,
                          )}
                        </p>
                        <div className='flex items-center justify-center h-full'>
                          <WidgetPreview type={selectedWidget.type} />
                        </div>

                        <div className='self-end'>
                          <Button onClick={handleInsertWidgetButtonClick}>
                            {t('header.edit-layout.actions.insert')}
                          </Button>
                        </div>
                      </>
                    )}
                  </div>
                </div>
              </PopoverContent>
            </Popover>
          );
        })}
      </div>

      <div>
        <Button
          onClick={handleSaveButtonClick}
          pending={isUpdateDashboardPending}
        >
          {t('header.edit-layout.actions.save')}
        </Button>
      </div>
    </div>
  );
}

interface WidgetGroup {
  name: string;
  widgets: {
    name: string;
    type: DashboardWidgetType;
  }[];
}

const widgetGroups: WidgetGroup[] = [
  {
    name: 'journals',
    widgets: [
      {
        name: 'journal-collection',
        type: DashboardWidgetType.JOURNAL_COLLECTION,
      },
      {
        name: 'single-journal',
        type: DashboardWidgetType.SINGLE_JOURNAL,
      },
    ],
  },
];

const WidgetPreview = ({ type }: { type: DashboardWidgetType }) => {
  const widget = Widgets[type];

  return (
    <div>
      <widget.preview />
    </div>
  );
};
