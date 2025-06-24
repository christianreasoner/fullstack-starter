import Button from '@material-ui/core/Button'
import Checkbox from '@material-ui/core/Checkbox'
import Dialog from '@material-ui/core/Dialog'
import DialogActions from '@material-ui/core/DialogActions'
import DialogContent from '@material-ui/core/DialogContent'
import DialogTitle from '@material-ui/core/DialogTitle'
import FormikNativeDatePicker from '../Form/DatePicker'
import Grid from '@material-ui/core/Grid'
import { MeasurementUnits } from '../../constants/units'
import React from 'react'
import TextField from '../Form/TextField'
import { Field, Form, Formik } from 'formik'
import { FormControlLabel, MenuItem } from '@material-ui/core'

class InventoryFormModal extends React.Component {
  render() {
    const unitOptions = Object.entries(MeasurementUnits).map(
      ([key, value]) => ({ value: key, label: value.name })
    )

    const {
      formName,
      handleDialog,
      handleInventory,
      title,
      initialValues,
      products
    } = this.props

    const productOptions = (products || []).map(product => ({
      value: product.name,
      label: product.name
    }))

    return (
      <Dialog
        open={this.props.isDialogOpen}
        maxWidth='sm'
        fullWidth={true}
        onClose={() => { handleDialog(false) }}
      >
        <Formik
          initialValues={initialValues}
          validate={values => {
            const errors = {}
            if (!values.name) {
              errors.name = 'Required'
            }
            if (!values.productType) {
              errors.productType = 'Required'
            }
            if (!values.unitOfMeasurement) {
              errors.unitOfMeasurement = 'Required'
            }
            return errors
          }}
          onSubmit={values => {
            handleInventory(values)
            handleDialog(true)
          }}>
          {helpers =>
            <Form
              noValidate
              autoComplete='off'
              id={formName}
            >
              <DialogTitle id='alert-dialog-title'>
                {`${title} Inventory`}
              </DialogTitle>
              <DialogContent>
                <Grid container spacing={4}>
                  <Grid item xs={7} sm={7}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='name'
                      label='Name'
                      required
                      component={TextField}
                    />
                  </Grid>
                  <Grid item xs={5} sm={5}>
                    <Field
                      name='productType'
                      label='Product Type'
                      required
                      component={TextField}
                      select
                      custom={{ variant: 'outlined', fullWidth: true }}
                    >
                      {productOptions.map((option) =>
                        <MenuItem key={option.value} value={option.value}>
                          {option.label}
                        </MenuItem>
                      )}
                    </Field>
                  </Grid>
                  <Grid item xs={12} sm={12}>
                    <Field
                      name='description'
                      label='Description'
                      component={TextField}
                      custom={{
                        variant: 'outlined',
                        fullWidth: true,
                        multiline: true,
                        rows: 3,
                      }}
                    />
                  </Grid>
                  <Grid item xs={6} sm={6}>
                    <Field
                      name='averagePrice'
                      label='Average Price'
                      component={TextField}
                      type='number'
                      custom={{ variant: 'outlined', fullWidth: true }}
                    />
                  </Grid>
                  <Grid item xs={6} sm={6}>
                    <Field
                      name='amount'
                      label='Amount'
                      component={TextField}
                      type='number'
                      custom={{ variant: 'outlined', fullWidth: true }}
                    />
                  </Grid>
                  <Grid item xs={6} sm={6}>
                    <Field
                      name='unitOfMeasurement'
                      label='Unit of Measurement'
                      required
                      component={TextField}
                      select
                      custom={{ variant: 'outlined', fullWidth: true }}
                    >
                      {unitOptions.map((option) =>
                        <MenuItem key={option.value} value={option.value}>
                          {option.label}
                        </MenuItem>
                      )}
                    </Field>
                  </Grid>
                  <Grid item xs={6} sm={6}>
                    <FormikNativeDatePicker name="bestBeforeDate" label="Best Before Date" />
                  </Grid>
                  <Grid item xs={6} sm={6}>
                    <Field name="neverExpires">
                      {({ field }) =>
                        <FormControlLabel
                          control={<Checkbox {...field} checked={field.value} color="primary" />}
                          label="Never Expires"
                          style={{ color: 'rgba(255, 255, 255, .67)' }}
                        />
                      }
                    </Field>
                  </Grid>
                </Grid>
              </DialogContent>
              <DialogActions>
                <Button onClick={() => { handleDialog(false) }} color='secondary'>Cancel</Button>
                <Button
                  disableElevation
                  variant='contained'
                  type='submit'
                  form={formName}
                  color='secondary'
                  disabled={!helpers.dirty}>
                  Save
                </Button>
              </DialogActions>
            </Form>
          }
        </Formik>
      </Dialog>
    )
  }
}

export default InventoryFormModal