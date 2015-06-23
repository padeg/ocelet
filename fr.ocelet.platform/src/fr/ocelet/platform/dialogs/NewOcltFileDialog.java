package fr.ocelet.platform.dialogs;

import org.eclipse.core.internal.resources.OS;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Dialog box to obtain the file name for a new Ocelet file. Automatically
 * appends .oclt at the end of the file if it was not already provided. The
 * first character will be forced to Uppercase.
 * 
 * @author Pascal Degenne - Initial contributor
 */
@SuppressWarnings("restriction")
public class NewOcltFileDialog extends TitleAreaDialog {

	private Text tFileName;
	private String fileName;

	public NewOcltFileDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void create() {
		super.create();
		setTitle("New Ocelet file");
		getButton(IDialogConstants.OK_ID).setEnabled(false);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(1, false);
		container.setLayout(layout);
		createNameLine(container);
		return area;
	}

	private void createNameLine(Composite container) {
		Label lbtFirstName = new Label(container, SWT.NONE);
		lbtFirstName.setText("File name");

		GridData dataFirstName = new GridData();
		dataFirstName.grabExcessHorizontalSpace = true;
		dataFirstName.horizontalAlignment = GridData.FILL;

		tFileName = new Text(container, SWT.BORDER);
		tFileName.setLayoutData(dataFirstName);
		tFileName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent mev) {
				if (OS.isNameValid(tFileName.getText()))
					getButton(IDialogConstants.OK_ID).setEnabled(true);
				else
					getButton(IDialogConstants.OK_ID).setEnabled(false);
			}
		});
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	private void saveInput() {
		String rawname;
		rawname = tFileName.getText().trim();
		rawname = rawname.replace(' ', '_');
		if (!rawname.isEmpty()) {
			if (!rawname.endsWith(".oclt"))
				rawname = rawname + ".oclt";
			fileName = Character.toString(rawname.charAt(0)).toUpperCase()
					+ rawname.substring(1);
		}
	}

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}

	public String getFileName() {
		return fileName;
	}
}
